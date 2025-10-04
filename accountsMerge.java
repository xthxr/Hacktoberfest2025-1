// User function Template for Java

class Solution {
    static ArrayList<ArrayList<String>> accountsMerge(ArrayList<ArrayList<String>> accounts) {
        Map<String, Integer> etId = new HashMap<>();
        Map<String, String> etName = new HashMap<>();
        int id = 0;
        int n = accounts.size();
        DSU dsu = new DSU(10000);
        
        for (int i = 0; i < accounts.size(); i++) {
            ArrayList<String> acc = accounts.get(i);
            String name = acc.get(0);
            for (int j = 1; j < acc.size(); j++) {
                String email = acc.get(j);
                if (!etId.containsKey(email)) {
                    etId.put(email, id++);
                    etName.put(email, name);
                }
                dsu.union(etId.get(acc.get(1)), etId.get(email));
            }
        }
        Map<Integer, TreeSet<String>> components = new HashMap<>();
        for (String email : etId.keySet()) {
            int parent = dsu.find(etId.get(email));
            components.computeIfAbsent(parent, x -> new TreeSet<>()).add(email);
        }
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (TreeSet<String> emails : components.values()) {
            ArrayList<String> merged = new ArrayList<>();
            String name = etName.get(emails.first());
            merged.add(name);
            merged.addAll(emails);
            result.add(merged);
        }
        return result;
    }
    static class DSU {
        int[] parent;
        public DSU(int N) {
            parent = new int[N];
            for (int i = 0; i < N; ++i)
                parent[i] = i;
        }
        public int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }
        public void union(int x, int y) {
            parent[find(x)] = find(y);
        }
    }
}
