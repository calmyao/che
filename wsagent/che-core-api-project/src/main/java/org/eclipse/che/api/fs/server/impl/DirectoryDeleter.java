/*
 * Copyright (c) 2012-2017 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.api.fs.server.impl;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.commons.io.FileUtils;
import org.eclipse.che.api.core.NotFoundException;
import org.eclipse.che.api.core.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
class DirectoryDeleter {

  private static final Logger LOG = LoggerFactory.getLogger(DirectoryDeleter.class);

  private final StandardFsPaths pathResolver;

  @Inject
  DirectoryDeleter(StandardFsPaths pathResolver) {
    this.pathResolver = pathResolver;
  }

  void delete(String wsPath) throws NotFoundException, ServerException {
    try {
      FileUtils.deleteDirectory(pathResolver.toFsPath(wsPath).toFile());
    } catch (IOException e) {
      throw new ServerException("Failed to delete directory " + wsPath, e);
    }
  }

  boolean deleteQuietly(String wsPath) {
    try {
      FileUtils.deleteDirectory(pathResolver.toFsPath(wsPath).toFile());
      return true;
    } catch (IOException e) {
      LOG.error("Failed to quietly delete directory: " + wsPath, e);
      return false;
    }
  }
}
