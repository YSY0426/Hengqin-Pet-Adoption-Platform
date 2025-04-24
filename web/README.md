# Frontend Documentation

## 🔧 Features

- Vite-based development and build  
- Modular API, view, and route structure  
- ESLint code quality enforcement  
- CDN-based Ant Design Vue integration  
- Environment-based configuration management  

---

## 🚀 Deployment Steps

1. **Modify the base URL** in `constants.ts`:  
   - Set the appropriate API endpoint for `BASE_URL`.

2. **Build the project** using Vite:
   ```bash
   vite build
   ```

3. **Deploy the `dist` folder** to your Nginx server.

---

## ⚙️ Configuration Overview

- `env.development`: Environment variables for development.
- `vite.config.js`: Vite configuration including aliases and server settings.
- `.eslintrc.js`: ESLint configuration for code formatting and linting rules.

---

## 🛠️ Development Guide

Frontend development is divided into three main steps:

### 1. API Development

Create a new file under the `api` directory and define your requests, e.g.:

```ts
import { get, post } from '/@/utils/http/axios';

enum URL {
  list = '/myapp/admin/notice/list',
  create = '/myapp/admin/notice/create',
  update = '/myapp/admin/notice/update',
  delete = '/myapp/admin/notice/delete',
}

const listApi = async (params: any) => get<any>({ url: URL.list, params });
const createApi = async (data: any) => post<any>({
  url: URL.create,
  data,
  headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' },
});
const updateApi = async (params: any, data: any) => post<any>({
  url: URL.update,
  params,
  data,
  headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' },
});
const deleteApi = async (params: any) => post<any>({ url: URL.delete, params });

export { listApi, createApi, updateApi, deleteApi };
```

### 2. View Development

Create a new `.vue` file in the `views` folder and implement your UI logic.

### 3. Route Configuration

Update `router/root.js` with the corresponding route:

```js
{ path: 'overview', name: 'overview', component: () => import('/@/views/overview.vue') },
{ path: 'asset', name: 'asset', component: () => import('/@/views/asset.vue') },
{ path: 'thing', name: 'thing', component: () => import('/@/views/thing.vue') },
{ path: 'comment', name: 'comment', component: () => import('/@/views/comment.vue') },
{ path: 'user', name: 'user', component: () => import('/@/views/user.vue') },
```

---

## ❓ Common Issues

- **Environment variables**: Refer to [this guide](https://blog.csdn.net/qq_41636947/article/details/117907448) for help.
- **Ant Design Vue CSS**:
  - Use CDN links in `index.html`:
    ```
    https://cdn.jsdelivr.net/npm/ant-design-vue@3.2.20/dist/
    https://cdn.staticfile.org/ant-design-vue/3.2.20/antd.min.css
    ```

---

## 📄 Notes

- Files in the `public` directory will automatically be included in the `dist` output after build.

---

如果你希望我将其导出为 Markdown 或 PDF 文件也可以告诉我。需要进一步美化或者添加logo、结构图等，也可以继续完善。
