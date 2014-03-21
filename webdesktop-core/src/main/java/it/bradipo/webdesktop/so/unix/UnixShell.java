package it.bradipo.webdesktop.so.unix;

import it.bradipo.webdesktop.shell.StringShell;

import java.io.IOException;

class UnixShell extends StringShell {

	public UnixShell() throws IOException {
		super();
	}

	@Override
	protected String getShellCommand() {
		return "/bin/bash";
	}

}
