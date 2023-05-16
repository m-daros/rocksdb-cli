package com.nttdata.tim.rocksdb.cli.repository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.SerializationUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

@Slf4j
@Repository
public class RocksDBReadWriteRepository implements KVRepository<String, Object> {

	@Value ( "${rocksdb.folder.path}" )
	private String dbFolderPath;

	File    baseDir;
	RocksDB db;


	@PostConstruct
	void init () {

		try {

			RocksDB.loadLibrary ();
			final Options options = new Options ();
			options.setCreateIfMissing ( true );
			baseDir = new File ( dbFolderPath );

			Files.createDirectories ( baseDir.getParentFile ().toPath () );
			Files.createDirectories ( baseDir.getAbsoluteFile ().toPath () );
			db = RocksDB.open ( options, baseDir.getAbsolutePath () );
			log.info ( "RocksDB initialized" );
		}
		catch ( Exception e ) {

			log.error ( "Error initializng RocksDB. Exception: '{}', message: '{}'", e.getCause (), e.getMessage (), e );
		}
	}

	@Override
	public boolean put ( String key, Object value ) {

		try {

			log.info ( "saving value '{}' with key '{}'", value, key );
			db.put ( key.getBytes (), SerializationUtils.serialize ( value ) );

			return true;
		}
		catch ( Exception e ) {

			log.error ( "Error saving entry. Cause: '{}', message: '{}'", e.getCause (), e.getMessage () );

			return false;
		}
	}

	@Override
	public Optional<Object> get ( String key ) {

		try {

			byte [] bytes = db.get ( key.getBytes () );

			Object value = bytes != null ? SerializationUtils.deserialize ( bytes ) : null;

			log.info ( "finding key '{}' returns '{}'", key, value );

			return value != null ? Optional.of ( value ) : Optional.empty ();
		}
		catch ( RocksDBException e ) {

			log.error ( "Error retrieving the entry with key: {}, cause: {}, message: {}", key, e.getCause (), e.getMessage () );

			return Optional.empty ();
		}
	}

	@Override
	public boolean delete ( String key ) {

		try {

			log.info ( "deleting key '{}'", key );
			db.delete ( key.getBytes () );

			return true;
		}
		catch ( RocksDBException e ) {

			log.error ( "Error deleting entry, cause: '{}', message: '{}'", e.getCause (), e.getMessage () );

			return false;
		}
	}
}