package config

import (
	"fmt"
	"os"
	"strconv"

	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/TheTenzou/gis-diplom/user-service/service"
)

type Services struct {
	UserService interfaces.UserService
	AuthService interfaces.AuthService
}

// InitServices init all necessary services
func InitServices(repositories *Repositories) (*Services, error) {
	userService := service.NewUserService(repositories.UserRepository)

	accessTokenSecret := os.Getenv("ACCESS_TOKEN_SECRET")
	accessTokenExpiration := os.Getenv("ACCESS_TOKEN_EXPIRATION")

	accessTokenExpirationSec, err := strconv.ParseInt(accessTokenExpiration, 0, 64)
	if err != nil {
		return nil, fmt.Errorf("could not ACCESSTOKEN_EXPIRATION as int: %v", err)
	}

	refreshTokenSecret := os.Getenv("REFRESH_TOKEN_SECRET")
	refreshTokenExpiration := os.Getenv("REFRESH_TOKEN_EXPIRATION")

	refreshTokenExpirationSec, err := strconv.ParseInt(refreshTokenExpiration, 0, 64)
	if err != nil {
		return nil, fmt.Errorf("could not REFRESHTOKEN_EXPIRATION as int: %v", err)
	}

	authService := service.NewAuthService(service.AuthServiceConfig{
		UserRepository:         repositories.UserRepository,
		TokenRepository:        repositories.TokenRepository,
		AccessTokenSecret:      accessTokenSecret,
		AccessTokenExpiration:  accessTokenExpirationSec,
		RefreshTokenSecret:     refreshTokenSecret,
		RefreshTokenExpiration: refreshTokenExpirationSec,
	})

	return &Services{
		UserService: userService,
		AuthService: authService,
	}, nil
}
