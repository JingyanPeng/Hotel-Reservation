package model;

public interface IRoom {
    default String getRoomNumber() {
        return null;
    }

    default double getRoomPrice() {
        return 0;
    }

    default RoomType getRoomType() {
        return null;
    }

    default boolean isFree() {
        return false;
    }
}
