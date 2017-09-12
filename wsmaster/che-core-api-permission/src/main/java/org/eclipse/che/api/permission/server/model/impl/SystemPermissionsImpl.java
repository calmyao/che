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
package org.eclipse.che.api.permission.server.model.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.eclipse.che.api.permission.server.SystemDomain;

/**
 * System permissions data object.
 *
 * @author Max Shaposhnik
 */
@Entity(name = "SystemPermissions")
@NamedQueries({
  @NamedQuery(
    name = "SystemPermissions.getByUserId",
    query =
        "SELECT permissions "
            + "FROM SystemPermissions permissions "
            + "WHERE permissions.userId = :userId "
  ),
  @NamedQuery(
    name = "SystemPermissions.getAll",
    query = "SELECT permissions " + "FROM SystemPermissions permissions "
  ),
  @NamedQuery(
    name = "SystemPermissions.getTotalCount",
    query = "SELECT COUNT(permissions) " + "FROM SystemPermissions permissions "
  )
})
@Table(name = "che_systempermissions")
public class SystemPermissionsImpl extends AbstractPermissions {

  public SystemPermissionsImpl() {}

  public SystemPermissionsImpl(String userId, List<String> actions) {
    super(userId);
    if (actions != null) {
      this.actions = new ArrayList<>(actions);
    }
  }

  public SystemPermissionsImpl(SystemPermissionsImpl permissions) {
    this(permissions.getUserId(), permissions.getActions());
  }

  @ElementCollection(fetch = FetchType.EAGER)
  @Column(name = "actions")
  @CollectionTable(name = "che_systempermissions_actions")
  protected List<String> actions;

  @Override
  public String getInstanceId() {
    return null;
  }

  @Override
  public String getDomainId() {
    return SystemDomain.DOMAIN_ID;
  }

  @Override
  public List<String> getActions() {
    return actions;
  }

  @Override
  public String toString() {
    return "SystemPermissions{" + "user='" + getUserId() + '\'' + ", actions=" + actions + '}';
  }
}