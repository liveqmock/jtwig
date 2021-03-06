/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jtwig.unit;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.jtwig.Environment;
import org.jtwig.exception.ResourceException;
import org.jtwig.loader.Loader;
import org.jtwig.unit.AbstractJtwigTest;
import org.jtwig.util.LoaderUtil;
import org.junit.Before;
import static org.mockito.Mockito.*;
import org.mockito.internal.stubbing.answers.ReturnsArgumentAt;

public abstract class MultiresourceUnitTest extends AbstractJtwigTest {
    protected Map<String, Loader.Resource> resources;
    
    @Before
    @Override
    public void before() throws Exception {
        super.before();
        resources = new HashMap<>();
    }
    @Override
    protected Environment buildEnvironment() {
        return spy(new Environment());
    }
    

    @Override
    protected void withResource(String template) {
        super.withResource(template);
        try {
            doAnswer(new ReturnsArgumentAt(0)).when(resource).resolve(any(String.class));
        } catch (ResourceException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    protected void withResource(final String name, final String contents) throws ResourceException {
        Loader.Resource tmp = mock(Loader.Resource.class);
        when(tmp.source()).thenReturn(new ByteArrayInputStream(contents.getBytes()));
        when(tmp.relativePath()).thenReturn(name);
        when(tmp.canonicalPath()).thenReturn(name);
        when(tmp.getCacheKey()).thenReturn(LoaderUtil.getCacheKey(name));
        when(env.load(name)).thenReturn(tmp);
        doAnswer(new ReturnsArgumentAt(0)).when(tmp).resolve(any(String.class));
        resources.put(name, tmp);
    }
    
//    protected void withPrimaryResource(final String contents) throws ResourceException {
//        withPrimaryResource("_primary_", contents);
//    }
//    protected void withPrimaryResource(final String name, final String contents) throws ResourceException {
//        when(env.load(name)).thenReturn(resource);
//        when(resource.source()).thenReturn(new ByteArrayInputStream(contents.getBytes()));
//        when(resource.relativePath()).thenReturn(name);
//        when(resource.canonicalPath()).thenReturn(name);
//        resources.put(name, resource);
//    }
//    protected void attachResource(final String name, final String contents) throws ResourceException {
//        Loader.Resource tmp = mock(Loader.Resource.class);
//        when(tmp.source()).thenReturn(new ByteArrayInputStream(contents.getBytes()));
//        when(tmp.relativePath()).thenReturn(name);
//        when(tmp.canonicalPath()).thenReturn(name);
//        when(env.load(name)).thenReturn(tmp);
//        resources.put(name, tmp);
//    }
}