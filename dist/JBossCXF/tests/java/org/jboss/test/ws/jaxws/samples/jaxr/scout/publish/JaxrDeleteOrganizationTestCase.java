/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.ws.jaxws.samples.jaxr.scout.publish;

import org.jboss.test.ws.jaxws.samples.jaxr.scout.JaxrAbstractBase;

import javax.xml.registry.BulkResponse;
import javax.xml.registry.JAXRException;
import javax.xml.registry.JAXRResponse;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.Organization;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Checks Deletion of Organization
 *
 * @author <mailto:Anil.Saldhana@jboss.org>Anil Saldhana
 * @since Jan 3, 2005
 */
public class JaxrDeleteOrganizationTestCase extends JaxrAbstractBase
{
   public String saveOrg(String orgname) throws JAXRException
   {
      String keyid = "";
      login();

      getJAXREssentials();
      Collection orgs = new ArrayList();
      Organization org = createOrganization("JBOSS");

      orgs.add(org);
      BulkResponse br = blm.saveOrganizations(orgs);
      if (br.getStatus() == JAXRResponse.STATUS_SUCCESS)
      {
         log.debug("Organization Saved");
         Collection coll = br.getCollection();
         Iterator iter = coll.iterator();
         while (iter.hasNext())
         {
            Key key = (Key)iter.next();
            keyid = key.getId();
            log.debug("Saved Key=" + key.getId());
            assertNotNull(keyid);
         }//end while
      }
      else
      {
         log.error("JAXRExceptions " + "occurred during save:");
         Collection exceptions = br.getExceptions();
         Iterator iter = exceptions.iterator();
         while (iter.hasNext())
         {
            Exception e = (Exception)iter.next();
            log.error(e.toString());
            fail(e.toString());
         }
      }

      return keyid;
   }

   public void testDeleteOrgs() throws Exception
   {
      String keyid = this.saveOrg("DELETEORG");
      assertNotNull(keyid);
      Key key = blm.createKey(keyid);
      this.deleteOrganization(key);
   }
}
