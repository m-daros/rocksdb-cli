package com.nttdata.tim.rocksdb.cli.commands;

import com.nttdata.tim.rocksdb.cli.repository.RocksDBReadWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class PutCommand {

	@Autowired
	private RocksDBReadWriteRepository rocksDbRepository;

	@ShellMethod ( value = "Put value using key.", key = "put" )
	public void put ( String key, Object value ) {

		System.out.println ( "put value: " + value + " using key: " + key );

		rocksDbRepository.put ( key, value );

		// TODO Error handling
	}
}