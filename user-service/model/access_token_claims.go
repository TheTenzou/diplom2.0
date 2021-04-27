package model

import "github.com/dgrijalva/jwt-go"

type TokenClaims struct {
	Roles []string `json:"roles,omitempty"`
	jwt.StandardClaims
}
