package repository

import (
	"context"
	"fmt"

	"github.com/TheTenzou/diplom2.0/user-service/interfaces"
	"github.com/TheTenzou/diplom2.0/user-service/model"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

type mongoUserRepository struct {
	MongoDB *mongo.Client
}

func NewMongoUserRepository(mongoDB *mongo.Client) interfaces.UserRepository {
	return &mongoUserRepository{
		MongoDB: mongoDB,
	}
}

func (r *mongoUserRepository) FindByID(
	ctx context.Context,
	userID primitive.ObjectID,
) (model.User, error) {

	collection := r.MongoDB.Database("users").Collection("users")

	var user model.User
	err := collection.FindOne(ctx, model.User{ID: userID}).Decode(&user)
	if err != nil {
		return user, fmt.Errorf("failed to fetch user: %v", err)
	}

	return user, nil
}

func (r *mongoUserRepository) FindByLogin(
	ctx context.Context,
	userLogin string,
) (model.User, error) {

	collection := r.MongoDB.Database("users").Collection("users")

	var user model.User
	err := collection.FindOne(ctx, model.User{Login: userLogin}).Decode(&user)
	if err != nil {
		return user, fmt.Errorf("failed to fetch user: %v", err)
	}

	return user, nil
}

func (r *mongoUserRepository) FindAll(
	ctx context.Context,
	page int,
) ([]model.User, error) {
	panic("not implemented")
}

func (r *mongoUserRepository) Create(
	ctx context.Context,
	user model.User,
) (model.User, error) {

	collection := r.MongoDB.Database("users").Collection("users")

	userID, err := collection.InsertOne(ctx, user)
	if err != nil {
		return model.User{}, fmt.Errorf("fail %v", err)
	}

	user.ID = userID.InsertedID.(primitive.ObjectID)

	return user, nil
}

func (r *mongoUserRepository) Update(
	ctx context.Context,
	user model.User,
) error {
	collection := r.MongoDB.Database("users").Collection("users")

	_, err := collection.UpdateByID(ctx, user.ID, bson.M{"$set": user})

	if err != nil {
		return fmt.Errorf("fail %v", err)
	}

	return nil
}

func (r *mongoUserRepository) Delete(
	ctx context.Context,
	userID primitive.ObjectID,
) (model.User, error) {

	collection := r.MongoDB.Database("users").Collection("users")

	result := collection.FindOneAndDelete(ctx, bson.M{"_id": userID})

	var deletedUser model.User

	err := result.Decode(&deletedUser)
	if err != nil {
		return model.User{}, fmt.Errorf("fail %v", err)
	}

	return deletedUser, nil
}
