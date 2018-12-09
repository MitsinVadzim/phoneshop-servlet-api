<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<t:header_footer title="Minicart">
    <table>
        <thead>
        <tr>
            <td>Name Link</td>
            <td>
                Link
            </td>
        </tr>
        <c:forEach var="minicartLink" items="${mappingList}">
            <tr>
                <td>
                        ${minicartLink.nameLink}
                </td>
                <td>
                    <a href="${minicartLink.link}">
                            ${minicartLink.link}
                    </a>
                </td>
            </tr>
        </c:forEach>
        </thead>
    </table>
</t:header_footer>