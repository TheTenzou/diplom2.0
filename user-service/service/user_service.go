package service

import (
	"context"

	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/TheTenzou/gis-diplom/user-service/model"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type userService struct {
	UserRepository interfaces.UserRepository
}

// factory function for initializing a UserService with its repository layer dependencies
func NewUserService(userRepository interfaces.UserRepository) interfaces.UserService {
	return &userService{
		UserRepository: userRepository,
	}
}

// fetch user by id from databse
// userID is id of requested user
func (s *userService) FindByID(ctx context.Context, userID primitive.ObjectID) (model.User, error) {
	return s.UserRepository.FindByID(ctx, userID)
}

// fetch user by login
// userLogin is login of requested user
func (s *userService) FindByLogin(ctx context.Context, userLogin string) (model.User, error) {
	return s.UserRepository.FindByLogin(ctx, userLogin)
}

// return page of users
// page is number of requested page
// pageSize is size of page
func (s *userService) FindAll(ctx context.Context, page int) ([]model.User, error) {
	return s.UserRepository.FindAll(ctx, page)
}

// create user record
// user login shoud be unique
// return inserted use with id
func (s *userService) Create(ctx context.Context, user model.User) (model.User, error) {
	return s.UserRepository.Create(ctx, user)
}

// update user record
// user login shoud be unique
func (s *userService) Update(ctx context.Context, user model.User) (model.User, error) {
	return s.UserRepository.Update(ctx, user)
}

// mark user is deleted
// return deleted user
func (s *userService) Delete(ctx context.Context, userID primitive.ObjectID) (model.User, error) {
	return s.UserRepository.Delete(ctx, userID)
}
