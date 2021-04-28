package interfaces

import (
	"context"

	"github.com/TheTenzou/gis-diplom/user-service/model"
)

// AuthService defines methods the handler layer expects
type AuthService interface {
	// return token pair for given user
	// rrequired login and passwrod in user structure to be filled
	Login(ctx context.Context, user model.User) (model.TokenPair, error)
	// validate access token
	// return user from token data
	ValidateAccessToken(token string) (model.User, error)
	// return new pair of tokens based on current refresh token
	RefreshTokens(ctx context.Context, refreshToken string) (model.TokenPair, error)
	BlackListToken(ctx context.Context, refreshToken string) error
}
