<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kotabek
  Date: 4/20/17
  Time: 1:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:import url="base/head.jsp"/>
    <title>Dashboard</title>
</head>
<body>
<div class="container">
    <a class="pull-right" href="/j_spring_security_logout">Logout</a>
    <h1>Please choose MP4 file</h1>

    <form method="POST"
          id="uploadForm"
          class="form-signin"
          enctype="multipart/form-data">

        <input type="file"
               id="file"
               name="file"
               class="form-control"
               accept="video/mp4"
               onchange="setFileInfo(this.files)"/>

        <input type="submit" class="btn btn-lg btn-primary btn-block" value="Upload"/>
    </form>
    <script type="application/javascript">
        var dur = 0;
        var form = $('#uploadForm');
        form.on('submit', function (e) {
            e.preventDefault();
            submmitForm();
        });
        function submmitForm() {

            if (dur > 600) {
                alert("Video is longer than 10 minutes");
            } else if (dur === 0) {
                alert("Please select a file to upload");
            } else {
                var formData = new FormData();
                jQuery.each(jQuery('#file')[0].files, function (i, file) {
                    formData.append('file', file);
                });
                $.ajax({
                    url: '/upload-file',
                    type: 'POST',
                    success: completeHandler = function (data) {
                        alert(data.message);
                        window.location.reload();
                    },
                    error: errorHandler = function () {
                        alert("Something went wrong!");
                    },
                    data: formData,
                    cache: false,
                    contentType: false,
                    processData: false
                }, 'json');
            }
        }
        //todo will implement validation on UI, later
        function setFileInfo(files) {
            if (files && files.length > 0) {
                var video = document.createElement('video');
                video.preload = 'metadata';
                video.onloadedmetadata = function () {
                    window.URL.revokeObjectURL(this.src);
                    dur = video.duration;
                }
                video.src = URL.createObjectURL(files[0]);
            } else {
                dur = 0;
            }
        }
    </script>
    <table class="table">
        <thead>
            <tr>
                <th>Name</th>
                <th>Duration (seconds)</th>
                <td>BitRate</td>
                <td>FrameRate</td>
                <td>FrameSize</td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="doc" items="${documents}">
                <tr>
                    <td><a href="${doc.url}">${doc.name}</a></td>
                    <td>${doc.duration}</td>
                    <td>${doc.bitRate}</td>
                    <td>${doc.frameRate}</td>
                    <td>${doc.frameSize}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</div>

<c:import url="base/footer.jsp"/>
</body>
</html>
