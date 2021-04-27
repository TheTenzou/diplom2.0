package service

import (
	"context"

	"github.com/TheTenzou/gis-diplom/user-service/apperrors"
	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/TheTenzou/gis-diplom/user-service/model"
	"github.com/TheTenzou/gis-diplom/user-service/utils"
)

type authService struct {
	userRepository interfaces.UserRepository
}

type AuthServiceConfig struct {
	UserRepository interfaces.UserRepository
}

func NewAuthSerivce(config AuthServiceConfig) interfaces.AuthService {
	return &authService{
		userRepository: config.UserRepository,
	}
}

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

	// for test purposes
	return model.TokenPair{
		AccessToken:  fetchedUser.Login,
		RefreshToken: fetchedUser.Password,
	}, nil
}

func (s *authService) RefreshTokens(ctx context.Context, refreshToken string) (model.TokenPair, error) {
	panic("not implemented")
}

func (s *authService) BlackListToken(ctx context.Context, refreshToken string) error {
	panic("not implemented")
}
