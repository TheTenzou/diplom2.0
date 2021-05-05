package interfaces

import (
	"context"
	"time"
)

// TokenRepository defines methods it expects a repository
type TokenRepository interface {
	// SaveRefreshToken stores a refresh token with an expiry time
	SaveRefreshToken(ctx context.Context, userID string, token string, expiresIn time.Duration) error
	// DeleteRefreshToken delete old refresh tokens
	DeleteRefreshToken(ctx context.Context, userID string, token string) error
	// DeleteUserRefreshTokens delete allUsers refresh tokens
	DeleteUserRefreshTokens(ctx context.Context, userID string) error
}
