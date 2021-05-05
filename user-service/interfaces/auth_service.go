package interfaces

import (
	"context"

	"github.com/TheTenzou/gis-diplom/user-service/model"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

// AuthService defines methods the handler layer expects
type AuthService interface {
	// Login return token pair for given user
	// required login and password in user structure to be filled
	Login(ctx context.Context, user model.User) (model.TokenPair, error)
	// ValidateAccessToken validate access token
	// return user from token data
	ValidateAccessToken(token string) (model.User, error)
	// RefreshTokens return new pair of tokens based on current refresh token
	RefreshTokens(ctx context.Context, refreshToken string) (model.TokenPair, error)
	// BlackListTokens blacklist all users tokens
	BlackListTokens(ctx context.Context, userID primitive.ObjectID) error
}
