package utils

import (
	"fmt"
	"log"
	"time"

	"github.com/TheTenzou/gis-diplom/user-service/model"
	"github.com/dgrijalva/jwt-go"
)

func GenerateRefreshToken(user model.User, key string, expiration int64) (string, error) {
	unixTime := time.Now().Unix()
	tokenExpire := unixTime + expiration

	claims := model.TokenClaims{
		Roles: user.Role,
		StandardClaims: jwt.StandardClaims{
			Subject:   user.ID.Hex(),
			IssuedAt:  unixTime,
			ExpiresAt: tokenExpire,
		},
	}

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	signedToken, err := token.SignedString([]byte(key))

	if err != nil {
		log.Println("Failed to sign refresh token string")
		return "", err
	}

	return signedToken, nil
}

func ValidateRefreshToken(tokenString string, key string) (*model.TokenClaims, error) {
	claims := &model.TokenClaims{}
	token, err := jwt.ParseWithClaims(tokenString, claims, func(token *jwt.Token) (interface{}, error) {
		return []byte(key), nil
	})

	if err != nil {
		return nil, err
	}

	if !token.Valid {
		return nil, fmt.Errorf("refresh token is invalid")
	}

	claims, ok := token.Claims.(*model.TokenClaims)

	if !ok {
		return nil, fmt.Errorf("refresh token valid but couldn't parse claims")
	}

	return claims, nil
}
