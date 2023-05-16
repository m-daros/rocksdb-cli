package com.nttdata.tim.rocksdb.cli.commands;

import com.nttdata.tim.rocksdb.cli.repository.KVRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Slf4j
@ShellComponent
public class GetCommand {

	@Autowired
	private KVRepository<String, Object> rocksDbRepository;

	@ShellMethod ( value = "Get value by key.", key = "get" )
	public void get ( String key ) {

		log.info ( "get value by key: " + key );

		rocksDbRepository.get ( key );

		// TODO Error handling
	}
}