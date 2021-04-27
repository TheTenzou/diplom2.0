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
	RefreshTokens(ctx context.Context, refreshToken string) (model.TokenPair, error)
	BlackListToken(ctx context.Context, refreshToken string) error
}
