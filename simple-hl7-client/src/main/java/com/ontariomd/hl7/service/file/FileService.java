package com.ontariomd.hl7.service.file;

import java.io.File;

public interface FileService {

	String read(File file, String encoding) throws FileServiceException;
}
