package org.agatom.springatom.webmvc.controllers.ru;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.agatom.springatom.core.web.CollectionDataResource;
import org.agatom.springatom.core.web.PageDataResource;
import org.agatom.springatom.data.services.SRecentlyUpdatedService;
import org.agatom.springatom.data.support.rupdate.RecentUpdateBean;
import org.agatom.springatom.web.api.RecentlyUpdatedController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collection;

import static org.agatom.springatom.web.api.RecentlyUpdatedController.Api.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SVRecentlyUpdatedControllerTest {

    @Mock
    private SRecentlyUpdatedService     recentlyUpdatedService;
    @InjectMocks
    private SVRecentlyUpdatedController recentlyUpdatedController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    // TODO: add unit testing for secured methods in another class

    @Test
    public void testGetAll_EmptyData() throws Exception {
        final MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(this.recentlyUpdatedController)
                .build();

        Mockito.when(this.recentlyUpdatedService.getRecentlyUpdated()).thenReturn(Lists.<RecentUpdateBean>newArrayList());
        mockMvc.perform(
                get(ROOT + GET_ALL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.getEmptyCollectionDataResource()))
                .andReturn();
    }

    private String getEmptyCollectionDataResource() throws JsonProcessingException {
        final CollectionDataResource<RecentUpdateBean> resource = new CollectionDataResource<>(Lists.<RecentUpdateBean>newArrayList());
        ReflectionTestUtils.setField(resource, "links", this.getAllSelfAndPagePageLinks());
        return new ObjectMapper().writeValueAsString(resource);
    }

    private Object getAllSelfAndPagePageLinks() {
        final Collection<Link> links = Lists.newArrayListWithExpectedSize(2);
        links.add(new Link("http://localhost" + RecentlyUpdatedController.Api.ROOT + RecentlyUpdatedController.Api.GET_ALL));
        links.add(new Link("http://localhost" + RecentlyUpdatedController.Api.ROOT + RecentlyUpdatedController.Api.GET_PAGE, "page"));
        return links;
    }

    @Test
    public void testGetPage_Empty() throws Exception {
        final PageRequest mock = Mockito.mock(PageRequest.class);
        Mockito.when(mock.getPageNumber()).thenReturn(0);
        Mockito.when(mock.getPageSize()).thenReturn(50);

        final MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(this.recentlyUpdatedController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver() {
                    @Override
                    public Pageable resolveArgument(final MethodParameter methodParameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
                        return mock;
                    }
                })
                .build();

        final PageImpl<RecentUpdateBean> page = new PageImpl<>(Lists.<RecentUpdateBean>newArrayList(), mock, 0);

        Mockito.when(this.recentlyUpdatedService.getRecentlyUpdated(mock))
                .thenReturn(page);

        mockMvc.perform(
                get(ROOT + GET_PAGE)
                        .param("page", "0")
                        .param("size", "50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(this.getEmptyPageDataResource(page)))
                .andReturn();
    }

    private String getEmptyPageDataResource(final PageImpl<RecentUpdateBean> emptyPage) throws JsonProcessingException {
        final PageDataResource<RecentUpdateBean> resource = new PageDataResource<>(emptyPage);
        ReflectionTestUtils.setField(resource, "links", this.getPageSelfAndAllLinks());
        return new ObjectMapper().writeValueAsString(resource);
    }

    private Object getPageSelfAndAllLinks() {
        final Collection<Link> links = Lists.newArrayListWithExpectedSize(2);
        links.add(new Link("http://localhost" + RecentlyUpdatedController.Api.ROOT + RecentlyUpdatedController.Api.GET_PAGE));
        links.add(new Link("http://localhost" + RecentlyUpdatedController.Api.ROOT + RecentlyUpdatedController.Api.GET_ALL, "all"));
        return links;
    }
}