package interfaces

import (
	"context"
	"time"
)

// TokenRepository defines methods it expects a repository
type TokenRepository interface {
	// stores a refresh token with an expiry time
	SaveRefreshToken(ctx context.Context, userID string, token string, expriresIn time.Duration) error
	// delete old refresh tokens
	DeleteRefreshToken(ctx context.Context, userID string, token string) error
	// delete allUsers refresh tokens
	DeleteUserRefreshTokens(ctx context.Context, userID string) error
}
