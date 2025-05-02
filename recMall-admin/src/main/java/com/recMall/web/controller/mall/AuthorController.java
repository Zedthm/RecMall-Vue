package com.recMall.web.controller.mall;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.recMall.common.annotation.Log;
import com.recMall.common.core.controller.BaseController;
import com.recMall.common.core.domain.AjaxResult;
import com.recMall.common.enums.BusinessType;
import com.recMall.mall.domain.Author;
import com.recMall.mall.service.IAuthorService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 书籍作者信息Controller
 * 
 * @author recMall
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/mall/author")
public class AuthorController extends BaseController
{
    @Autowired
    private IAuthorService authorService;

    /**
     * 查询书籍作者信息列表
     */
    @PreAuthorize("@ss.hasPermi('mall:author:list')")
    @GetMapping("/list")
    public TableDataInfo list(Author author)
    {
        startPage();
        List<Author> list = authorService.selectAuthorList(author);
        return getDataTable(list);
    }

    /**
     * 导出书籍作者信息列表
     */
    @PreAuthorize("@ss.hasPermi('mall:author:export')")
    @Log(title = "书籍作者信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Author author)
    {
        List<Author> list = authorService.selectAuthorList(author);
        ExcelUtil<Author> util = new ExcelUtil<Author>(Author.class);
        util.exportExcel(response, list, "书籍作者信息数据");
    }

    /**
     * 获取书籍作者信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:author:query')")
    @GetMapping(value = "/{authorId}")
    public AjaxResult getInfo(@PathVariable("authorId") String authorId)
    {
        return success(authorService.selectAuthorByAuthorId(authorId));
    }

    /**
     * 新增书籍作者信息
     */
    @PreAuthorize("@ss.hasPermi('mall:author:add')")
    @Log(title = "书籍作者信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Author author)
    {
        return toAjax(authorService.insertAuthor(author));
    }

    /**
     * 修改书籍作者信息
     */
    @PreAuthorize("@ss.hasPermi('mall:author:edit')")
    @Log(title = "书籍作者信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Author author)
    {
        return toAjax(authorService.updateAuthor(author));
    }

    /**
     * 删除书籍作者信息
     */
    @PreAuthorize("@ss.hasPermi('mall:author:remove')")
    @Log(title = "书籍作者信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{authorIds}")
    public AjaxResult remove(@PathVariable String[] authorIds)
    {
        return toAjax(authorService.deleteAuthorByAuthorIds(authorIds));
    }
}
