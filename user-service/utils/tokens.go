package utils

import (
	"log"
	"time"

	"github.com/TheTenzou/gis-diplom/user-service/model"
	"github.com/dgrijalva/jwt-go"
)

func GenerateAccessToken(user model.User, secret string, expiration int64) (string, error) {
	unixTime := time.Now().Unix()
	tokenExpire := unixTime + expiration

	claims := model.AccessTokenClaims{
		Subject:   user.ID.Hex(),
		Roles:     user.Role,
		IssuedAt:  unixTime,
		ExpiresAt: tokenExpire,
	}

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	signedToken, err := token.SignedString([]byte(secret))

	if err != nil {
		log.Printf("Failed to sign id token string: %v", err)
		return "", err
	}

	return signedToken, nil
}
