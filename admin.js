async function loadPendingProviders() {
    try {
        //const res = await fetch("http://localhost:8080/api/admin/providers/pending");
        const res = await fetch("http://localhost:8080/api/admin/providers/pending");
        const data = await res.json();

        console.log("Pending providers:", data);
    } catch (err) {
        console.error("Error:", err);
    }
}

loadPendingProviders();