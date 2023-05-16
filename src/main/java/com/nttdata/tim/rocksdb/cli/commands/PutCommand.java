package com.nttdata.tim.rocksdb.cli.commands;

import com.nttdata.tim.rocksdb.cli.repository.KVRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Slf4j
@ShellComponent
public class PutCommand {

	@Autowired
	private KVRepository<String, Object> rocksDbRepository;

	@ShellMethod ( value = "Put value providing key and value. Example: put key value", key = "put" )
	public void put ( String key, Object value ) {

		log.debug ( "put value: " + value + " using key: " + key );

		rocksDbRepository.put ( key, value );
	}
}