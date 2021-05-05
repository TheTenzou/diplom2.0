package repository

import (
	"context"
	"fmt"
	"log"
	"time"

	"github.com/TheTenzou/gis-diplom/user-service/apperrors"
	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/go-redis/redis/v8"
)

type redisTokenRepository struct {
	Redis *redis.Client
}

// NewTokenRepository factory for initializing User Repositories
func NewTokenRepository(redisClient *redis.Client) interfaces.TokenRepository {
	return &redisTokenRepository{
		Redis: redisClient,
	}
}

// SaveRefreshToken stores a refresh token with an expiry time
func (r *redisTokenRepository) SaveRefreshToken(
	ctx context.Context,
	userID string,
	token string,
	expiresIn time.Duration,
) error {

	key := fmt.Sprintf("%s.%s", userID, token)
	if err := r.Redis.Set(ctx, key, 0, expiresIn).Err(); err != nil {
		log.Printf("Could not SET refresh token to redis for userID/tokenID: %s/%s: %v\n", userID, token, err)
		return apperrors.NewInternal()
	}

	return nil
}

// DeleteRefreshToken delete old refresh tokens
func (r *redisTokenRepository) DeleteRefreshToken(ctx context.Context, userID string, token string) error {

	key := fmt.Sprintf("%s.%s", userID, token)

	result := r.Redis.Del(ctx, key)

	if err := result.Err(); err != nil {
		log.Printf("Could not delete refresh token to redis for userID/tokenID: %s/%s: %v\n", userID, token, err)
		return apperrors.NewInternal()
	}

	if result.Val() < 1 {
		log.Printf("Refresh token to redis for userID/tokenID %s/%s doesnot exist\n", userID, token)
		return apperrors.NewUnauthorized("Invalid refresh token")
	}

	return nil
}

// DeleteUserRefreshTokens delete all refresh tokens of specific user
func (r *redisTokenRepository) DeleteUserRefreshTokens(ctx context.Context, userID string) error {
	pattern := fmt.Sprintf("%s*", userID)

	iter := r.Redis.Scan(ctx, 0, pattern, 5).Iterator()
	failCount := 0

	for iter.Next(ctx) {
		if err := r.Redis.Del(ctx, iter.Val()).Err(); err != nil {
			log.Printf("Failed to delete refresh token: %s\n", iter.Val())
			failCount++
		}
	}

	// check last value
	if err := iter.Err(); err != nil {
		log.Printf("Failed to delete refresh token: %s\n", iter.Val())
	}

	if failCount > 0 {
		return apperrors.NewInternal()
	}

	return nil
}
