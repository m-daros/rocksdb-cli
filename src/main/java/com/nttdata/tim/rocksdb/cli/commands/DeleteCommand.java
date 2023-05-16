package com.nttdata.tim.rocksdb.cli.commands;

import com.nttdata.tim.rocksdb.cli.repository.RocksDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DeleteCommand {

	@Autowired
	private RocksDBRepository rocksDbRepository;

	@ShellMethod ( value = "Get value by key.", key = "delete" )
	public void delete ( String key ) {

		System.out.println ( "delete value by key: " + key );

		rocksDbRepository.delete ( key );

		// TODO Error handling
	}
}