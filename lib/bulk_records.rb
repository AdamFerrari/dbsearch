class BulkRecords

  def self.load_lines(path, max_to_create = -1)
    num_created = 0
    File.open(path).each do |line|
      unless line.blank?
        record = Record.new
        record.content = line
        record.save!
        num_created = num_created + 1
      end
      if ((max_to_create > 0) && (num_created >= max_to_create))
        return nil
      end
    end
    nil
  end



end

