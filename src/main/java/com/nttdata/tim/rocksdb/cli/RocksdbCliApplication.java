package com.nttdata.tim.rocksdb.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RocksdbCliApplication {

	public static void main ( String [] args ) {

		SpringApplication.run ( RocksdbCliApplication.class, args );
	}
}