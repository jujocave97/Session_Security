package com.es.seguridadsession;

import com.es.seguridadsession.utils.Crypter;

import java.util.Scanner;

public class HashearPassMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce contraseña");
        String pass = sc.nextLine();

        System.out.println(Crypter.hashPassword(pass));

        //INSERT INTO `usuarios`(`admin`, `id`, `nombre`, `password`) VALUES (true,1,'aa','9834876dcfb05cb167a5c24953eba58c4ac89b1adf57f28f2f9d09af107ee8f0')
    }
}
