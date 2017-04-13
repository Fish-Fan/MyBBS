<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="header-bar">
    <div class="container">
        <a href="/index.do" class="brand">
            <i class="fa fa-reddit-alien"></i>
        </a>
        <ul class="unstyled inline pull-right">
            <c:choose>
                <c:when test="${not empty sessionScope.curr_user}">
                    <li>
                        <a href="#">
                            <img src="http://ok2crkjlq.bkt.clouddn.com/${sessionScope.curr_user.avatar}?imageView2/1/w/20/h/20" class="img-circle avatar1" alt="">
                        </a>
                    </li>
                    <li>
                        <a href="/topic/new.do"><i class="fa fa-plus"></i></a>
                    </li>
                    <li>
                        <a href="/user/message"><i class="fa fa-bell"></i>
                            <c:if test="${msgNum != 0}" >
                                <span class="badge" style="background-image: linear-gradient(to right, #f83600 0%, #f9d423 100%);">
                                        ${msgNum}
                                </span>
                            </c:if>

                        </a>
                    </li>
                    <li>
                        <a href="/user/setting.do"><i class="fa fa-cog"></i></a>
                    </li>
                    <li>
                        <a href="/loginOut.do"><i class="fa fa-sign-out"></i></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li>
                        <a href="/login.do"><i class="fa fa-sign-in"></i></a>
                    </li>
                </c:otherwise>
            </c:choose>

        </ul>
    </div>
</div>