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

// init all necessary services
func InitServices(repositories *Repositories) (*Services, error) {
	userService := service.NewUserService(repositories.UserRepository)

	accessTokenSecret := os.Getenv("ACCESSTOKEN_SECRET")
	accessTokenExpiration := os.Getenv("ACCESSTOKEN_EXPIRATION")

	accessTokenExpirationSec, err := strconv.ParseInt(accessTokenExpiration, 0, 64)
	if err != nil {
		return nil, fmt.Errorf("could not ACCESSTOKEN_EXPIRATION as int: %v", err)
	}

	refreshTokenSecret := os.Getenv("REFRESHTOKEN_SECRET")
	refreshTokenExpiration := os.Getenv("REFRESHTOKEN_EXPIRATION")

	refreshTokenExpirationSec, err := strconv.ParseInt(refreshTokenExpiration, 0, 64)
	if err != nil {
		return nil, fmt.Errorf("could not REFRESHTOKEN_EXPIRATION as int: %v", err)
	}

	authService := service.NewAuthSerivce(service.AuthServiceConfig{
		UserRepository:         repositories.UserRepository,
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
