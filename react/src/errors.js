export function handleAPIError(response, ret=true) {
    const contentType = response.headers.get("content-type");
    if (contentType && contentType.indexOf("application/json") !== -1) {
        if (ret) {
            return response.json().then(data => {
                alert(data.message);
            });
        }
        response.json().then(data => {
            alert(data.message);
        });
    } else {
        if (ret) {
            return response.text().then(text => {
                alert(text);
            });
        }
        response.text().then(text => {
            alert(text);
        });
    }
}