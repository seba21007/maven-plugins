package org.apache.maven.plugin.plugin;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.installer.ArtifactInstaller;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.AbstractPlugin;
import org.apache.maven.plugin.PluginExecutionRequest;
import org.apache.maven.plugin.PluginExecutionResponse;
import org.apache.maven.project.MavenProject;

/**
 * @goal install
 *
 * @description installs project's main artifact in local repository
 *
 * @parameter name="project"
 * type="org.apache.maven.project.MavenProject"
 * required="true"
 * validator=""
 * expression="#project"
 * description=""
 * @parameter name="installer"
 * type="org.apache.maven.artifact.installer.ArtifactInstaller"
 * required="true"
 * validator=""
 * expression="#component.org.apache.maven.artifact.installer.ArtifactInstaller"
 * description=""
 * @parameter name="localRepository"
 * type="org.apache.maven.artifact.repository.ArtifactRepository"
 * required="true"
 * validator=""
 * expression="#localRepository"
 * description=""
 * @prereq jar:jar
 */
public class PluginInstallMojo
    extends AbstractPlugin
{
    public void execute( PluginExecutionRequest request, PluginExecutionResponse response )
        throws Exception
    {
        MavenProject project = (MavenProject) request.getParameter( "project" );

        ArtifactInstaller artifactInstaller = (ArtifactInstaller) request.getParameter( "installer" );

        ArtifactRepository localRepository = (ArtifactRepository) request.getParameter( "localRepository" );

        Artifact artifact = new DefaultArtifact( project.getGroupId(),
                                                 project.getArtifactId(),
                                                 project.getVersion(),
                                                 project.getType() );

        artifactInstaller.install( project.getBuild().getDirectory(), artifact, localRepository );

        // ----------------------------------------------------------------------
        // This is not the way to do this, but in order to get the integration
        // tests working this is what I'm doing. jvz.
        // ----------------------------------------------------------------------

        Artifact pomArtifact = new DefaultArtifact( project.getGroupId(),
                                                    project.getArtifactId(),
                                                    project.getVersion(),
                                                    "pom" );

        artifactInstaller.install( project.getFile(), pomArtifact, localRepository );
    }
}
