package service

import (
	"context"
	"log"

	"github.com/TheTenzou/gis-diplom/user-service/interfaces"
	"github.com/TheTenzou/gis-diplom/user-service/model"
	"github.com/TheTenzou/gis-diplom/user-service/utils"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type userService struct {
	UserRepository interfaces.UserRepository
}

// NewUserService factory function for initializing a UserService with its repository layer dependencies
func NewUserService(userRepository interfaces.UserRepository) interfaces.UserService {
	return &userService{
		UserRepository: userRepository,
	}
}

// FindByID fetch user by id from database
// userID is id of requested user
func (s *userService) FindByID(ctx context.Context, userID primitive.ObjectID) (model.User, error) {
	return s.UserRepository.FindByID(ctx, userID)
}

// FindByLogin fetch user by login
// userLogin is login of requested user
func (s *userService) FindByLogin(ctx context.Context, userLogin string) (model.User, error) {
	return s.UserRepository.FindByLogin(ctx, userLogin)
}

// FindAll return page of users
// page is number of requested page
// pageSize is size of page
func (s *userService) FindAll(ctx context.Context, page model.Pagination) (model.PaginatedData, error) {
	return s.UserRepository.FindAll(ctx, page)
}

// Create create user record
// user login should be unique
// return inserted use with id
func (s *userService) Create(ctx context.Context, user model.User) (model.User, error) {
	var err error
	user.Password, err = utils.HashPassword(user.Password)
	if err != nil {
		log.Printf("Failed to hash password: %v", err)
	}
	return s.UserRepository.Create(ctx, user)
}

// Update update user record
// user login should be unique
func (s *userService) Update(ctx context.Context, user model.User) (model.User, error) {
	return s.UserRepository.Update(ctx, user)
}

// Delete mark user is deleted
// return deleted user
func (s *userService) Delete(ctx context.Context, userID primitive.ObjectID) (model.User, error) {
	return s.UserRepository.Delete(ctx, userID)
}
