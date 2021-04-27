package model

import "github.com/dgrijalva/jwt-go"

type AccessTokenClaims struct {
	Subject   string   `json:"sub,omitempty"`
	Roles     []string `json:"roles,omitempty"`
	IssuedAt  int64    `json:"iat,omitempty"`
	ExpiresAt int64    `json:"exp,omitempty"`
	jwt.StandardClaims
}
