package utils

import (
	"fmt"
	"log"
	"time"

	"github.com/TheTenzou/gis-diplom/user-service/model"
	"github.com/dgrijalva/jwt-go"
)

func GenerateAccessToken(user model.User, secret string, expiration int64) (string, error) {
	unixTime := time.Now().Unix()
	tokenExpire := unixTime + expiration

	claims := model.AccessTokenClaims{
		Roles: user.Role,
		StandardClaims: jwt.StandardClaims{Subject: user.ID.Hex(),
			IssuedAt:  unixTime,
			ExpiresAt: tokenExpire,
		},
	}

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	signedToken, err := token.SignedString([]byte(secret))

	if err != nil {
		log.Printf("Failed to sign id token string: %v", err)
		return "", err
	}

	return signedToken, nil
}

func ValidateAccessToken(tokenString string, secret string) (*model.AccessTokenClaims, error) {
	claims := &model.AccessTokenClaims{}

	token, err := jwt.ParseWithClaims(
		tokenString, claims,
		func(token *jwt.Token) (interface{}, error) {
			return []byte(secret), nil
		},
	)

	if err != nil {
		return nil, err
	}

	if !token.Valid {
		return nil, fmt.Errorf("ID token is invalid")
	}

	claims, ok := token.Claims.(*model.AccessTokenClaims)

	if !ok {
		return nil, fmt.Errorf("ID token valid but couldn't parse claims")
	}

	return claims, nil
}
