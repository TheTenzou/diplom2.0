package service

import (
	"context"
	"log"

	"github.com/TheTenzou/gis-diplom/user-service/apperrors"
	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/TheTenzou/gis-diplom/user-service/model"
	"github.com/TheTenzou/gis-diplom/user-service/utils"
)

type authService struct {
	userRepository        interfaces.UserRepository
	accessTokenSecret     string
	accessTokenExpiration int64
}

type AuthServiceConfig struct {
	UserRepository        interfaces.UserRepository
	AccessTokenSecret     string
	AccessTokenExpiration int64
}

// factory function for initializing a AuthService with its repository layer dependencies
func NewAuthSerivce(config AuthServiceConfig) interfaces.AuthService {
	return &authService{
		userRepository:        config.UserRepository,
		accessTokenSecret:     config.AccessTokenSecret,
		accessTokenExpiration: config.AccessTokenExpiration,
	}
}

// return token pair for given user
// rrequired login and passwrod in user structure to be filled
func (s *authService) Login(ctx context.Context, user model.User) (model.TokenPair, error) {
	fetchedUser, err := s.userRepository.FindByLogin(ctx, user.Login)
	if err != nil {
		return model.TokenPair{}, apperrors.NewUnauthorized("user with this login doesn't exist")
	}

	match, err := utils.ComparePasswords(fetchedUser.Password, user.Password)
	if err != nil {
		return model.TokenPair{}, apperrors.NewInternal()
	}

	if !match {
		return model.TokenPair{}, apperrors.NewUnauthorized("Invalid password")
	}

	accessToken, err := utils.GenerateAccessToken(fetchedUser, s.accessTokenSecret, s.accessTokenExpiration)
	if err != nil {
		log.Printf("Error genaraating refreshToken for id: %v. Error %v\n", fetchedUser.ID, err.Error())
	}

	// for test purposes
	return model.TokenPair{
		AccessToken:  accessToken,
		RefreshToken: "dummy",
	}, nil
}

func (s *authService) ValidateAccessToken(token string) (model.User, error) {
	panic("not implemented")
}

func (s *authService) ValidateRefreshToken(token string) (model.User, error) {
	panic("not implemented")
}

func (s *authService) RefreshTokens(ctx context.Context, refreshToken string) (model.TokenPair, error) {
	panic("not implemented")
}

func (s *authService) BlackListToken(ctx context.Context, refreshToken string) error {
	panic("not implemented")
}
