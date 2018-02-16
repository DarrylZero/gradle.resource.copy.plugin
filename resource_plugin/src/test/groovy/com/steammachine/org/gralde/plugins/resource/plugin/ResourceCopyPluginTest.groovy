package com.steammachine.org.gralde.plugins.resource.plugin

import org.gradle.api.Task
import org.gradle.api.internal.project.DefaultProject
import org.gradle.api.tasks.Copy
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert
import org.junit.Test

class ResourceCopyPluginTest {

    @Test
    void testFillName() {
        Assert.assertEquals("com.steammachine.org.gralde.plugins.resource.plugin.ResourceCopyPlugin",
                ResourceCopyPlugin.class.name);
    }

    @Test
    void simpleSmokeTest10() {
        Assert.assertEquals("copyresources", ResourceCopyPlugin.EXTENSION_NAME);
        Assert.assertEquals("defaultclassresources", Resources.DEFAULT_COPY_CLASS_RESOURCES_TASK);
        Assert.assertEquals("defaultcopyresources", Resources.DEFAULT_COPY_TEST_RESOURCES_TASK);
        Assert.assertEquals("copyresources", Resources.COPY_RESOURCES_TASK);
        Assert.assertEquals(Collections.singletonList("**/**resource*"), Resources.DEFAULT_COPY_PATTERN);
    }

    @Test
    void test10() {
        DefaultProject project = (DefaultProject) ProjectBuilder.builder().build();

        Assert.assertNull(project.getPluginManager().findPlugin("java"));
        Assert.assertNull(project.getPluginManager().findPlugin("com.steammachine.org.gradle.copyresources.plugin"));
        project.getPluginManager().apply(ResourceCopyPlugin.class);
        Assert.assertNotNull(project.getPluginManager().findPlugin("java"));
        Assert.assertNotNull(project.getPluginManager().findPlugin("com.steammachine.org.gradle.copyresources.plugin"));
        project.evaluate();
    }

    @Test
    void test20() {
        DefaultProject project = (DefaultProject) ProjectBuilder.builder().build();

        Assert.assertNull(project.getPluginManager().findPlugin("java"));
        project.getPluginManager().apply("java");
        Assert.assertNotNull(project.getPluginManager().findPlugin("java"));
        project.evaluate()

        Task processResources = project.getTasks().stream().filter { 'processResources' == it.name }.
                findFirst().orElseThrow { new IllegalStateException() }

        Task processTestResources = project.getTasks().stream().filter { 'processTestResources' == it.name }.
                findFirst().orElseThrow { new IllegalStateException() }

        Assert.assertTrue(processResources.getDependsOn().stream().filter { Task.class.isInstance(it) }.
                map { Task.class.cast(it) }.map { it.name }.noneMatch { it == "copyClassResources" })

        Assert.assertTrue(processTestResources.dependsOn.stream().filter{Task.class.isInstance(it)}.map{Task.class.cast(it)}.
                map{it.name}.noneMatch{it =='copyTestResources'})
    }


    @Test
    void test30() {
        DefaultProject project = (DefaultProject) ProjectBuilder.builder().build();

        Assert.assertNull(project.getPluginManager().findPlugin('java'));
        Assert.assertNull(project.getPluginManager().findPlugin('com.steammachine.org.gradle.copyresources.plugin'));
        project.getPluginManager().apply('com.steammachine.org.gradle.copyresources.plugin');
        Assert.assertNotNull(project.getPluginManager().findPlugin('java'));
        Assert.assertNotNull(project.getPluginManager().findPlugin("com.steammachine.org.gradle.copyresources.plugin"));
        project.evaluate();

        Copy defaultclassresources = (Copy) project.getTasks().getByName(Resources.DEFAULT_COPY_CLASS_RESOURCES_TASK);
        Copy defaultcopyresources = (Copy) project.getTasks().getByName(Resources.DEFAULT_COPY_TEST_RESOURCES_TASK);
        Task copyresources = project.getTasks().getByName(Resources.COPY_RESOURCES_TASK);
        Task processResources = project.getTasks().getByName("processResources");

        Assert.assertTrue(copyresources.getDependsOn().contains(defaultclassresources));
        Assert.assertTrue(copyresources.getDependsOn().contains(defaultcopyresources));
        Assert.assertTrue(processResources.getDependsOn().contains(copyresources));
    }


    @Test
    void pluginCreation() {
        DefaultProject project = (DefaultProject) ProjectBuilder.builder().build();
        project.getPlugins().apply(ResourceCopyPlugin.class);
    }

    @Test
    void pluginCreation20() {
        DefaultProject project = (DefaultProject) ProjectBuilder.builder().build();
        project.getPlugins().apply(ResourceCopyPlugin.class);

        project.defaultcopyresources {
        }
        project.defaultcopyresources {
        }
        project.evaluate()
    }

    @Test
    void creation() {
        new Resources(ProjectBuilder.builder().build());
    }


    @Test
    void testPluginResourceFile() {
        getClass().classLoader.
                getResourceAsStream('META-INF/gradle-plugins/com.steammachine.org.gradle.copyresources.plugin.properties').withCloseable {
            def properties = new Properties()
            properties.load(it)

            Assert.assertEquals('com.steammachine.org.gralde.plugins.resource.plugin.ResourceCopyPlugin',
                    properties.getProperty('implementation-class'))
        }
    }





}
