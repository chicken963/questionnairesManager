class httpUtils {
    post(url, data) {
        return new Promise((resolve, reject) => {
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-type' : 'application/json'
                },
                body : JSON.stringify(data)
            })
                .then(res => res.json())
                .then(data => resolve(data))
                .catch(err => reject(err));
        });
    }

    put(url, data) {
        return new Promise((resolve, reject) => {
            fetch(url, {
                method: 'PUT',
                headers: {
                    'Content-type' : 'application/json'
                },
                body : JSON.stringify(data)
            })
                .then(res => res.json())
                .then(data => resolve(data))
                .catch(err => reject(err));
        });
    }


    get(url) {
        return new Promise((resolve, reject) => {
            fetch(url, {
                method: 'GET',
                headers: {
                    'Content-type' : 'application/json'
                }
            })
                .then(res => res.json())
                .then(data => resolve(data))
                .catch(err => reject(err));
        });
    }

    delete(url) {
        return new Promise((resolve, reject) => {
            fetch(url, {
                method: 'DELETE',
                headers: {
                    'Content-type' : 'application/json'
                }
            })
                .then(res => res.json())
                .then(data => resolve(data))
                .catch(err => reject(err));
        });
    }
}