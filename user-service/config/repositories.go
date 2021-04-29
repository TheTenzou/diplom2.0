package config

import (
	"context"
	"os"

	"github.com/TheTenzou/gis-diplom/user-service/config/databases"
	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/TheTenzou/gis-diplom/user-service/model"
	"github.com/TheTenzou/gis-diplom/user-service/repository"
	"github.com/TheTenzou/gis-diplom/user-service/utils"
	"go.mongodb.org/mongo-driver/mongo"
)

type Repositories struct {
	UserRepository  interfaces.UserService
	TokenRepository interfaces.TokenRepository
}

// initialise all necassary repositories
func InitRepositories() (*Repositories, error) {

	mongo, err := databases.GetMongo()
	if err != nil {
		return nil, err
	}

	dbName := os.Getenv("MONGO_DABASE_NAME")
	collectionName := os.Getenv("USERS_COLLECTION")
	usersCollection := mongo.Database(dbName).Collection(collectionName)
	createAdmin(usersCollection)

	userRepository := repository.NewMongoUserRepository(usersCollection)

	redis, err := databases.GetRedis()
	if err != nil {
		return nil, err
	}

	tokenRepository := repository.NewTokenRepository(redis)

	return &Repositories{
		UserRepository:  userRepository,
		TokenRepository: tokenRepository,
	}, nil
}

// insert admin user if not exist
func createAdmin(users *mongo.Collection) {
	password, _ := utils.HashPassword("admin")
	user := model.User{
		Login:    "admin",
		Password: password,
		Status:   "ACTIVE",
		Role:     []string{"ROLE_ADMIN", "ROLE_USER"},
	}

	users.InsertOne(context.Background(), user)
}
