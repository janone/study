package org.example.vavr;

import io.vavr.control.Either;

public class Main {

    public static void main(String[] args) {

        Either<String,String> either = Either.right("abc");
        boolean left = either.isLeft();
        boolean right = either.isRight();
        System.out.println("is left = " + left);
        System.out.println("is right = " + right);
        System.out.println("either.get() "+either.get());

        Either<String[], String> map = either.mapLeft(str -> str.split(""));
        Either<String[], String> strings = map.flatMap(str -> Either.right(str.toUpperCase()));
//        System.out.println(strings.getLeft());
        System.out.println(strings.get());


    }

}
