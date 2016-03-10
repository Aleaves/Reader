package com.app.reader;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.app.reader.adapter.FileAdapter;
import com.app.reader.utils.ReaderConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.Bind;

public class FileChooser extends BaseActivity {

    private List<Map<String,Object>> fileList;
    private FileAdapter adapter;
    /**
     * currentDirection:当前文件夹
     */
    private String currentDirection;
    @Bind(R.id.file_list)
    ListView listView;
    @Bind(R.id.toolbar_file_choose)
    Toolbar toolbar;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_file_chooser;
    }

    @Override
    protected void init() {
        currentDirection = Environment.getExternalStorageDirectory().getAbsolutePath();
        initData();
    }

    public void initData() {
        fileList = getFileList(currentDirection);
        toolbar.setTitle(currentDirection);

        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentDirection.equals(Environment.getExternalStorageDirectory().getAbsolutePath())) {
                    FileChooser.this.finish();
                }else{
                    fileBack();
                }
            }
        });

        listView.setOnItemClickListener(new OnItemClickListenerImpl());
        adapter = new FileAdapter(this, fileList, currentDirection);
        listView.setAdapter(adapter);

    }


    /**
     * 回到上级目录
     */
    private void fileBack() {
        File file = new File(currentDirection);

        if (file.getParent().equals("/")) {
            Toast.makeText(FileChooser.this, "已到顶目录了", Toast.LENGTH_SHORT).show();
        } else {
            currentDirection = file.getParent();
            updateItems();
        }
    }


    /**
     * 获取当前目录所有文件和文件夹
     * @param currentDirection
     * @return
     */
    private List<Map<String, Object>> getFileList(String currentDirection) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        File file = new File(currentDirection);

        File[] files = file.listFiles();
        int length;
        if (files == null) {
            length = 0;
        } else {
            length = files.length;
        }

        for (int i = 0; i < length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("filename", files[i].getName());
            list.add(map);
        }

        return list;
    }

    /**
     * 更新
     */
    public void updateItems() {
        adapter.notifyDataSetChanged();
        initData();
    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /**
             * 文件的绝对路径
             */
            String childFile = currentDirection + "/" + fileList.get(position).get("filename").toString();

            /**
             * 如果file是目录：点击进入子目录；
             *
             */

            if (new File(childFile).isDirectory()) {
                currentDirection = childFile;
                updateItems();
            } else {
                if (childFile.endsWith(".txt")) {

                    Intent intentForResult = new Intent();
                    intentForResult.putExtra(ReaderConstants.FILE_PATH,childFile);
                    intentForResult.putExtra(ReaderConstants.FILE_NAME, fileList.get(position).get("filename").toString());
                    FileChooser.this.setResult(1, intentForResult);
                    FileChooser.this.finish();
                } else {
                    Toast.makeText(FileChooser.this, "请选择txt文件", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
