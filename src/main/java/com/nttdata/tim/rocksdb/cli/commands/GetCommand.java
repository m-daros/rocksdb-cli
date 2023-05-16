package com.nttdata.tim.rocksdb.cli.commands;

import com.nttdata.tim.rocksdb.cli.repository.RocksDBReadWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class GetCommand {

	@Autowired
	private RocksDBReadWriteRepository rocksDbRepository;

	@ShellMethod ( value = "Get value by key.", key = "get" )
	public void get ( String key ) {

		System.out.println ( "get value by key: " + key );

		rocksDbRepository.get ( key );

		// TODO Error handling
	}
}