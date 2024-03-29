<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%--  
 ~ Copyright 2010 Google Inc.
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain  a copy of the License at
 ~
 ~      http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~
--%>
  <head><title>Hello Netkiller</title></head>
  <body>
    <div>
        Please enter your Google Apps domain to log in.<br/>

        <form action="<c:url value="/openid"/>" method="get">
            <div class="field">
                <label for="domain_field">Google Apps Domain</label><input id="domain_field" type="text" name="domain"/>
            </div>
            <div class="field">
                <input type="submit" value="Log in">
            </div>
        </form>
    </div>
  </body>
</html>