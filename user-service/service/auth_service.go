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

// validate access token
// return user from token data
func (s *authService) ValidateAccessToken(token string) (model.User, error) {
	claims, err := utils.ValidateAccessToken(token, s.accessTokenSecret)

	if err != nil {
		log.Printf("Unable to validate or parse accessToken - Error: %v\n", err)
		return model.User{}, apperrors.NewUnauthorized("Unable to verify user from accessToken")
	}

	userID, err := primitive.ObjectIDFromHex(claims.Subject)
	if err != nil {
		log.Printf("failed to parse userID: %v", err)
		return model.User{}, apperrors.NewUnauthorized("Unable to verify user from accessToken")
	}
	user := model.User{
		ID:   userID,
		Role: claims.Roles,
	}
	return user, nil
}

// return new pair of tokens based on current refresh token
func (s *authService) RefreshTokens(ctx context.Context, refreshToken string) (model.TokenPair, error) {
	claims, err := utils.ValidateRefreshToken(refreshToken, s.refreshTokenSecret)
	if err != nil {
		log.Printf("Unable to validate or parse refreshToken - Error: %v\n", err)
		return model.TokenPair{}, apperrors.NewUnauthorized("Unable to verify user from refreshToken")
	}

	userID, err := primitive.ObjectIDFromHex(claims.Subject)
	if err != nil {
		log.Printf("Unable to parse user id: %v", err)
		return model.TokenPair{}, apperrors.NewInternal()
	}

	user := model.User{
		ID:   userID,
		Role: claims.Roles,
	}

	err = s.tokenRepository.DeleteRefreshToken(ctx, userID.Hex(), refreshToken)
	if err != nil {
		log.Printf("Failed to delete refresh token: %v", err)
		return model.TokenPair{}, err
	}

	accessToken, err := utils.GenerateAccessToken(user, s.accessTokenSecret, s.accessTokenExpiration)
	if err != nil {
		log.Printf("Error generating accessToken for id: %v. Error %v\n", user.ID, err.Error())
		return model.TokenPair{}, apperrors.NewInternal()
	}

	newRefreshToken, err := utils.GenerateRefreshToken(user, s.refreshTokenSecret, s.refreshTokenExpiration)
	if err != nil {
		log.Printf("Error generating refreshToken for id: %v. Error %v\n", user.ID, err.Error())
	}

	err = s.tokenRepository.SaveRefreshToken(ctx, user.ID.Hex(), newRefreshToken, time.Duration(s.refreshTokenExpiration)*time.Second)
	if err != nil {
		log.Printf("Error savign refresh token: %v", err)
		return model.TokenPair{}, apperrors.NewInternal()
	}

	return model.TokenPair{
		AccessToken:  accessToken,
		RefreshToken: newRefreshToken,
	}, nil
}

func (s *authService) BlackListToken(ctx context.Context, refreshToken string) error {
	panic("not implemented")
}
