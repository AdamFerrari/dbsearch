class AddKeywordIndexToKeywords < ActiveRecord::Migration
  def change
    add_index :keywords, :keyword
  end
end
