package org.apache.spark;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.utils.WriterFactory;
import org.apache.maven.shared.utils.io.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

@Mojo(name = "effective-pom", defaultPhase = LifecyclePhase.INSTALL, threadSafe = true)
public class EffectivePomMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(defaultValue = "${project.build.directory}/effective-pom.xml")
    private String output;

    public void execute() throws MojoExecutionException {
        writeEffectivePom(new File(output));
    }

    private File writeEffectivePom(File output) throws MojoExecutionException {
        Model m = project.getModel();
        Writer writer = null;
        try {
            writer = WriterFactory.newXmlWriter(output);
            new MavenXpp3Writer().write(writer, m);
            getLog().info("Written effective pom at:" + output.getAbsolutePath());
            return output;
        } catch (IOException e) {
            throw new MojoExecutionException("Error writing file: " + e.getMessage(), e);
        } finally {
            IOUtil.close(writer);
        }
    }

}
