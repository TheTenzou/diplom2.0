package service

import (
	"context"
	"log"
	"time"

	"github.com/TheTenzou/gis-diplom/user-service/apperrors"
	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/TheTenzou/gis-diplom/user-service/model"
	"github.com/TheTenzou/gis-diplom/user-service/utils"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type authService struct {
	userRepository         interfaces.UserRepository
	tokenRepository        interfaces.TokenRepository
	accessTokenSecret      string
	accessTokenExpiration  int64
	refreshTokenSecret     string
	refreshTokenExpiration int64
}

type AuthServiceConfig struct {
	UserRepository         interfaces.UserRepository
	TokenRepository        interfaces.TokenRepository
	AccessTokenSecret      string
	AccessTokenExpiration  int64
	RefreshTokenSecret     string
	RefreshTokenExpiration int64
}

// factory function for initializing a AuthService with its repository layer dependencies
func NewAuthSerivce(config AuthServiceConfig) interfaces.AuthService {
	return &authService{
		userRepository:         config.UserRepository,
		tokenRepository:        config.TokenRepository,
		accessTokenSecret:      config.AccessTokenSecret,
		accessTokenExpiration:  config.AccessTokenExpiration,
		refreshTokenSecret:     config.RefreshTokenSecret,
		refreshTokenExpiration: config.RefreshTokenExpiration,
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
		log.Printf("Error generating accessToken for id: %v. Error %v\n", fetchedUser.ID, err.Error())
		return model.TokenPair{}, apperrors.NewInternal()
	}

	refreshToken, err := utils.GenerateRefreshToken(fetchedUser, s.refreshTokenSecret, s.refreshTokenExpiration)
	if err != nil {
		log.Printf("Error generating refreshToken for id: %v. Error %v\n", fetchedUser.ID, err.Error())
	}

	err = s.tokenRepository.SaveRefreshToken(ctx, fetchedUser.ID.Hex(), refreshToken, time.Duration(s.refreshTokenExpiration)*time.Second)
	if err != nil {
		log.Printf("Error savign refresh token: %v", err)
		return model.TokenPair{}, apperrors.NewInternal()
	}

	return model.TokenPair{
		AccessToken:  accessToken,
		RefreshToken: refreshToken,
	}, nil
}

func (s *authService) ValidateAccessToken(token string) (model.User, error) {
	claims, err := utils.ValidateAccessToken(token, s.accessTokenSecret)

	if err != nil {
		log.Printf("Unable to validate or parse accessToken - Error: %v\n", err)
		return model.User{}, apperrors.NewUnauthorized("Unable to verify user from accessToken")
	}

	userID, err := primitive.ObjectIDFromHex(claims.Subject)
	if err != nil {
		log.Printf("faild to parse userID: %v", err)
		return model.User{}, apperrors.NewUnauthorized("Unable to verify user from accessToken")
	}
	user := model.User{
		ID:   userID,
		Role: claims.Roles,
	}
	return user, nil
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
