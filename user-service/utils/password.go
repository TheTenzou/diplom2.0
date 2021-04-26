package utils

import (
	"crypto/rand"
	"encoding/hex"
	"fmt"

	"golang.org/x/crypto/scrypt"
)

func HashPassword(password string) (string, error) {
	salt := make([]byte, 32)

	_, err := rand.Read(salt)
	if err != nil {
		return "", err
	}

	hash, err := scrypt.Key([]byte(password), salt, 32768, 8, 1, 32)
	if err != nil {
		return "", err
	}

	hashedPassword := fmt.Sprintf(
		"%s.%s",
		hex.EncodeToString(hash),
		hex.EncodeToString(salt),
	)

	return hashedPassword, nil
}
