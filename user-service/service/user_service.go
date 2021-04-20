package service

import (
	"context"

	"github.com/TheTenzou/diplom2.0/user-service/interfaces"
	"github.com/TheTenzou/diplom2.0/user-service/model"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type userService struct {
	UserRepository interfaces.UserRepository
}

func NewUserService(userRepository interfaces.UserRepository) interfaces.UserService {
	return &userService{
		UserRepository: userRepository,
	}
}

func (s *userService) FindByID(ctx context.Context, userID primitive.ObjectID) (model.User, error) {
	return s.UserRepository.FindByID(ctx, userID)
}

func (s *userService) FindByLogin(ctx context.Context, userLogin string) (model.User, error) {
	return s.UserRepository.FindByLogin(ctx, userLogin)
}

func (s *userService) FindAll(ctx context.Context, page int) ([]model.User, error) {
	return s.UserRepository.FindAll(ctx, page)
}
func (s *userService) Create(ctx context.Context, user model.User) (model.User, error) {
	return s.UserRepository.Create(ctx, user)
}

func (s *userService) Update(ctx context.Context, user model.User) error {
	return s.UserRepository.Update(ctx, user)
}

func (s *userService) Delete(ctx context.Context, userID primitive.ObjectID) (model.User, error) {
	return s.UserRepository.Delete(ctx, userID)
}
