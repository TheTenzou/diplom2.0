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
	Users   *mongo.Collection
}

func NewMongoUserRepository(mongoDB *mongo.Client) interfaces.UserRepository {
	return &mongoUserRepository{
		MongoDB: mongoDB,
		Users:   mongoDB.Database("users").Collection("users"),
	}
}

func (r *mongoUserRepository) FindByID(
	ctx context.Context,
	userID primitive.ObjectID,
) (model.User, error) {

	var user model.User

	err := r.Users.FindOne(ctx, model.User{ID: userID}).Decode(&user)
	if err != nil {
		return user, fmt.Errorf("failed to fetch user by id: %v", err)
	}

	return user, nil
}

func (r *mongoUserRepository) FindByLogin(
	ctx context.Context,
	userLogin string,
) (model.User, error) {

	var user model.User

	err := r.Users.FindOne(ctx, model.User{Login: userLogin}).Decode(&user)
	if err != nil {
		return user, fmt.Errorf("failed to fetch user by login: %v", err)
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

	userID, err := r.Users.InsertOne(ctx, user)
	if err != nil {
		return model.User{}, fmt.Errorf("failed to create users %v", err)
	}

	user.ID = userID.InsertedID.(primitive.ObjectID)

	return user, nil
}

func (r *mongoUserRepository) Update(
	ctx context.Context,
	user model.User,
) error {

	_, err := r.Users.UpdateByID(ctx, user.ID, bson.M{"$set": user})

	if err != nil {
		return fmt.Errorf("failed to update user %v", err)
	}

	return nil
}

func (r *mongoUserRepository) Delete(
	ctx context.Context,
	userID primitive.ObjectID,
) (model.User, error) {

	result := r.Users.FindOneAndDelete(ctx, bson.M{"_id": userID})

	var deletedUser model.User

	err := result.Decode(&deletedUser)
	if err != nil {
		return model.User{}, fmt.Errorf("failed to delete user %v", err)
	}

	return deletedUser, nil
}
